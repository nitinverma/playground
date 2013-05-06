Installing brew
===

Better checkout http://mxcl.github.io/homebrew/

Homebrew for given versions
====

http://stackoverflow.com/questions/3987683/homebrew-install-specific-version-of-formula

Example:
For versions support:

    $ brew tap homebrew/versions

Installing elasticsearch

    $ brew search elasticsearch
    elasticsearch
    $ brew versions elasticsearch | head -5
    0.20.6   git checkout 3bffe56 /usr/local/Library/Formula/elasticsearch.rb
    0.20.5   git checkout f63e464 /usr/local/Library/Formula/elasticsearch.rb
    0.20.4   git checkout 0fb09e8 /usr/local/Library/Formula/elasticsearch.rb
    0.20.2   git checkout a00c97c /usr/local/Library/Formula/elasticsearch.rb
    0.20.1   git checkout c5592f7 /usr/local/Library/Formula/elasticsearch.rb
    $ brew install elasticsearch

