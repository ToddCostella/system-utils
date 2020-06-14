# system-utils

Utility Scripts to manage environment.

## update-hosts
Used to replace/add entries in /etc/hosts.

Assumes IP address to add is in system clipboard. Useful when spinning up digital ocean instances that I want to associate with hosts entry.

```
sudo update-hosts <alias>
```

If <alias> is not defined the default do-dev will be used.

## Utility Script

Copy update-hosts.groovy to /usr/local/bin/update-hosts

```shell script
sudo ./deploy-script.sh
```
## Root shell configuration
Groovy and Java need to be configured in the root shell as the update-hosts needs to run as root (due to changing /etc/hosts)

```shell script
sudo su
curl -s "https://get.sdkman.io" | bash
source "/root/.sdkman/bin/sdkman-init.sh"
sdk i groovy
sdk i java
ln -sf /root/.sdkman/candidates/groovy/current/bin/groovy /usr/bin/groovy
```

Add following line to /usr/bin/groovy
```
export JAVA_HOME=/root/.sdkman/candidates/java/11.0.7.hs-adpt
```