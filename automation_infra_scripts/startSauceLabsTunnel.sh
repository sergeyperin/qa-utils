#!/usr/bin/env bash
set -x
file_name=not_defined
projectPath=${PWD}
username=${SAUCE_USERNAME}
key=${SAUCE_ACCESS_KEY}
tunnelId=${REMOTE_TUNNEL_ID}
unamestr=$(uname)

if [[ "$unamestr" == 'Linux' ]]; then
  echo 'Detected linux platform'
  file_extension=.tar.gz
  file_name=sc-4.4.12-linux
  if [ ! -d ${file_name} ]; then
    echo "getting the latest version of sauce connect"
    find . -name 'sc-*' | xargs rm -fr
    wget "https://saucelabs.com/downloads/${file_name}${file_extension}"
    tar xf ${file_name}${file_extension}
    rm ${file_name}${file_extension}
  fi
elif [[ "$unamestr" == 'Darwin' ]]; then
  echo 'Detected MacOS platform'
  file_extension=.zip
  file_name=sc-4.4.9-osx
  tunnelId=$(hostname)
  if [ ! -d ${file_name} ]; then
    echo "getting the latest version of sauce connect"
    find . -name 'sc-*' | xargs rm -fr
    wget "https://saucelabs.com/downloads/${file_name}${file_extension}"
    unzip ${file_name}${file_extension}
  fi
else
  echo "Unknown OS type: ${unamestr}. Exiting..."
  exit -1
fi

echo "starting connection"
command="${projectPath}/${file_name}/bin/sc -v -u ${username} -k ${key} -i ${tunnelId} -B all"
echo ${command}
eval nohup ${command} >saucelabs_tunnel.log &

for i in {1..10}; do
  if grep -R "Sauce Connect is up, you may start your tests." "saucelabs_tunnel.log"; then
    echo "Tunnel is started"
    exit 0
  fi
  if grep -R "resource temporarily unavailable" "saucelabs_tunnel.log"; then
    echo "Tunnel cannot be started. Resource temporarily unavailable"
    exit 0
  fi
  echo "Tunnel not started yet. Attempt ${i} of 10. Waiting..."
  sleep 5
done
