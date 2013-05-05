
Python
===
    $ brew install python --with-brewed-openssl --framework --universal
    $ brew install python3 --with-brewed-openssl --framework --universal

distribute
===
    $ pip install --upgrade distribute

pip
===
    $ pip install --upgarde pip

virtualenv
===
   $ pip install virtualenv
   $ pip install virtualenvwrapper
   $ source /usr/local/share/python/virtualenvwrapper.sh



http://www.thisisthegreenroom.com/2011/installing-python-numpy-scipy-matplotlib-and-ipython-on-lion/
http://hackercodex.com/guide/python-virtualenv-on-mac-osx-mountain-lion-10.8/

==> Caveats
Homebrew's Python framework
  /usr/local/Cellar/python/2.7.4/Frameworks/Python.framework

Python demo
  /usr/local/share/python/Extras

Distribute and Pip have been installed. To update them
  pip install --upgrade distribute
  pip install --upgrade pip

To symlink "Idle" and the "Python Launcher" to ~/Applications
  `brew linkapps`

You can install Python packages with (the outdated easy_install or)
  `pip install <your_favorite_package>`

They will install into the site-package directory
  /usr/local/lib/python2.7/site-packages

Executable python scripts will be put in:
  /usr/local/share/python
so you may want to put "/usr/local/share/python" in your PATH, too.

See: https://github.com/mxcl/homebrew/wiki/Homebrew-and-Python
==> Summary


-----


==> Caveats
Homebrew's Python3 framework
  /usr/local/Cellar/python3/3.3.1/Frameworks/Python.framework

Distribute and Pip have been installed. To update them
  pip3 install --upgrade distribute
  pip3 install --upgrade pip

To symlink "Idle 3" and the "Python Launcher 3" to ~/Applications
  `brew linkapps`

You can install Python packages with
  `pip3 install <your_favorite_package>`

They will install into the site-package directory
  /usr/local/lib/python3.3/site-packages
Executable python scripts will be put in:
  /usr/local/share/python3
so you may want to put "/usr/local/share/python3" in your PATH, too.

See: https://github.com/mxcl/homebrew/wiki/Homebrew-and-Python
==> Summary

