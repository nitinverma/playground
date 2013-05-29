$ brew install Nexus
==> Downloading http://download.sonatype.com/nexus/oss/nexus-2.3.1-bundle.tar.gz
######################################################################## 100.0%
==> Patching
patching file nexus-2.3.1-01/conf/nexus.properties
==> Caveats
If you are upgrading nexus for the first time, and old version is less than 2.3.1, then
you will need to copy the sonatype-work directory from:
  /usr/local/Cellar/nexus/<old version>/
to
  /usr/local/var

To have launchd start nexus at login:
    ln -sfv /usr/local/opt/nexus/*.plist ~/Library/LaunchAgents
Then to load nexus now:
    launchctl load ~/Library/LaunchAgents/homebrew.mxcl.nexus.plist
Or, if you don't want/need launchctl, you can just run:
    /usr/local/opt/nexus/libexec/bin/nexus { console | start | stop | restart | status | dump }
==> Summary
🍺  /usr/local/Cellar/nexus/2.3.1: 736 files, 37M, built in 3.3 minutes

