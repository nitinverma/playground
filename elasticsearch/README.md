	$ brew install elasticsearch
	==> Downloading https://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-0.90.0.tar.gz
	######################################################################## 100.0%
	==> Caveats
	Data:    /usr/local/var/elasticsearch/elasticsearch_nitin/
	Logs:    /usr/local/var/log/elasticsearch/elasticsearch_nitin.log
	Plugins: /usr/local/var/lib/elasticsearch/plugins/
	
	To have launchd start elasticsearch at login:
	    ln -sfv /usr/local/opt/elasticsearch/*.plist ~/Library/LaunchAgents
	Then to load elasticsearch now:
	    launchctl load ~/Library/LaunchAgents/homebrew.mxcl.elasticsearch.plist
	Or, if you don't want/need launchctl, you can just run:
	    elasticsearch -f -D es.config=/usr/local/opt/elasticsearch/config/elasticsearch.yml
	==> Summary
	🍺  /usr/local/Cellar/elasticsearch/0.90.0: 30 files, 19M, built in 63 seconds


0.20.1 

        $ git checkout c5592f7 /usr/local/Library/Formula/elasticsearch.rb
	$ brew install elasticsearch
	==> Downloading http://download.elasticsearch.org/elasticsearch/elasticsearch/elasticsearch-0.20.1.tar.gz
	######################################################################## 100.0%
	==> Caveats
	If upgrading from 0.18 ElasticSearch requires flushing before shutting
	down the cluster with no indexing operations happening after flush:
	    curl host:9200/_flush
	
	See the 'elasticsearch.yml' file for configuration options.
	
	You'll find the ElasticSearch log here:
	    open /usr/local/var/log/elasticsearch/elasticsearch_nitin.log
	
	The folder with cluster data is here:
	    open /usr/local/var/elasticsearch/elasticsearch_nitin/
	
	You should see ElasticSearch running:
	    open http://localhost:9200/
	
	You should reload elasticsearch:
	    launchctl unload ~/Library/LaunchAgents/homebrew.mxcl.elasticsearch.plist
	    launchctl load ~/Library/LaunchAgents/homebrew.mxcl.elasticsearch.plist
	==> Summary
	🍺  /usr/local/Cellar/elasticsearch/0.20.1: 35 files, 19M, built in 62 seconds

