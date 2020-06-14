#!/usr/bin/env groovy
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

File tmpHosts = File.createTempFile("hosts", "txt")
File tmpKnownHosts = File.createTempFile("known_hosts","txt")

def newIp = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor)

//todo: Probably should confirm the clipboard contents are actually an IP address.
if (!newIp){
    println "update-hosts: No IP address on clipboard"
    return
}

def hostName = "dev-do"
if (args) {
    hostName = args[0]
}

def processed = false
new File("/etc/hosts").eachLine {
    if (it.contains(hostName)) {
        tmpHosts.append("$newIp\t$hostName\n")
        println "Updated /etc/hosts with host: $hostName and address: $newIp"
        processed = true
    } else {
        tmpHosts.append("$it\n")
    }
}

if (!processed){
    println "Adding /etc/hosts with host: $hostName and address: $newIp"
    tmpHosts.append("$newIp\t$hostName\n")
}


"rm /etc/hosts".execute()
"cp $tmpHosts.absolutePath /etc/hosts".execute()


new File("/home/todd/.ssh/known_hosts").eachLine {
    if (!it.startsWith(hostName)) {
        tmpKnownHosts.append("$it\n")
    } else {
        println "Removed $hostName from known_hosts"
    }
}

"rm /home/todd/.ssh/known_hosts".execute()
"cp $tmpKnownHosts.absolutePath /home/todd/.ssh/known_hosts".execute()
"chown -v todd /home/todd/.ssh/known_hosts".execute()

