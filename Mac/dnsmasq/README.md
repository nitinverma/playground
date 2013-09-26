 $ brew install dnsmasq
==> Downloading http://www.thekelleys.org.uk/dnsmasq/dnsmasq-2.66.tar.gz
######################################################################## 100.0%
==> make install PREFIX=/usr/local/Cellar/dnsmasq/2.66
==> Caveats
To configure dnsmasq, copy the example configuration to /usr/local/etc/dnsmasq.conf
and edit to taste.

  cp /usr/local/opt/dnsmasq/dnsmasq.conf.example /usr/local/etc/dnsmasq.conf

To have launchd start dnsmasq at startup:
    sudo cp -fv /usr/local/opt/dnsmasq/*.plist /Library/LaunchDaemons
Then to load dnsmasq now:
    sudo launchctl load /Library/LaunchDaemons/homebrew.mxcl.dnsmasq.plist
==> Summary
🍺  /usr/local/Cellar/dnsmasq/2.66: 7 files, 444K, built in 12 seconds

