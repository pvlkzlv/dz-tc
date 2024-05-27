cd ..

teamcity_tests_directory=$(pwd)
workdir="teamcity_tests_infrastructure"
teamcity_server_workdir="teamcity_server"
teamcity_agent_workdir="teamcity_agent"
selenoid_workdir="selenoid"
teamcity_server_container_name="teamcity_server_instance"
teamcity_agent_container_name="teamcity_agent_instance"
selenoid_container_name="selenoid_instance"
selenoid_ui_container_name="selenoid_ui_instance"

workdir_names=($teamcity_server_workdir $teamcity_agent_workdir $selenoid_workdir)
container_names=($teamcity_server_container_name $teamcity_agent_container_name $selenoid_container_name $selenoid_ui_container_name)

log() {
  echo "$(date '+%Y-%m-%d %H:%M:%S') - $1"
}

log "Request IP"
export ips=$(ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1')
export ip=${ips%%$'\n'*}
log "Current IP: $ip"

log "Delete previous run data"
rm -rf $workdir
mkdir -p $workdir
cd $workdir

for dir in "${workdir_names[@]}"; do
  mkdir -p $dir
done

for container in "${container_names[@]}"; do
  log "Stopping container: $container"
  docker stop $container || log "Container $container not running"
  log "Removing container: $container"
  docker rm $container || log "Container $container not found"
done

log "Start teamcity server"
cd $teamcity_server_workdir
mkdir -p logs

docker run -d --name $teamcity_server_container_name \
    -v $(pwd)/logs:/opt/teamcity/logs \
    -p 8111:8111 \
    jetbrains/teamcity-server:2023.11.1

log "Teamcity Server is running..."
log "Waiting for TeamCity server to initialize..."

until curl -s http://$ip:8111/ | grep -q "TeamCity"; do
  sleep 1
done

log "Start selenoid"
cd $teamcity_tests_directory/$workdir/$selenoid_workdir
mkdir -p config
cp $teamcity_tests_directory/infra/browsers.json config/

docker run -d --name $selenoid_container_name \
            -p 4444:4444 \
            -v /var/run/docker.sock:/var/run/docker.sock \
            -v $(pwd)/config/:/etc/selenoid/:ro \
            aerokube/selenoid:latest-release

log "Waiting for Selenoid to initialize..."
until curl -s http://$ip:4444/status | grep -q "browsers"; do
  sleep 1
done

log "Pull all browser images"
image_names=($(awk -F'"' '/"image": "/{print $4}' "$(pwd)/config/browsers.json"))
for image in "${image_names[@]}"; do
  log "Pulling image: $image"
  docker pull $image
done

log "Start selenoid ui"
docker run -d --name $selenoid_ui_container_name \
            -p 8080:8080 aerokube/selenoid-ui:latest-release --selenoid-uri http://$ip:4444

log "Waiting for Selenoid UI to initialize..."
until curl -s -o /dev/null -w '%{http_code}' http://$ip:8080/status | grep -q "200"; do
  sleep 1
done

log "Setup teamcity server"
cd $teamcity_tests_directory
mvn clean test -Dtest=TeamCitySetupTest#startUpTest

log "Parse superuser token"
logs_dir="$teamcity_tests_directory/$workdir/$teamcity_server_workdir/logs"

until [ -d "$logs_dir" ]; do
  log "Logs directory does not exist: $logs_dir"
  sleep 1
done

until grep -q 'Super user authentication token: [0-9]*' "$logs_dir/teamcity-server.log"; do
  log "Super user token not found in logs, waiting..."
  sleep 1
done

superuser_token=$(grep -o 'Super user authentication token: [0-9]*' "$logs_dir/teamcity-server.log" | grep -o '[0-9]*') || log "Super user token not found"
log "Super user token: $superuser_token"

log "Run system tests"
echo -e "host=$ip:8111\nsuperUserToken=$superuser_token\nremote=http://$ip:4444/wd/hub\nbrowser=chrome" > $teamcity_tests_directory/src/main/resources/config.properties
cat $teamcity_tests_directory/src/main/resources/config.properties

log "Run API tests"
mvn test -DsuiteXmlFile=testng-suites/api-suite.xml

log "Run UI tests"
mvn test -DsuiteXmlFile=testng-suites/ui-suite.xml

log "Script execution completed"
