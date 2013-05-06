#This is for Mac, Mountain Lion

[Python] [py]
[py]: http://en.wikipedia.org/wiki/Python_(programming_language)
===
    $ brew install python --with-brewed-openssl --framework --universal

Python3 is not that stable as of now but you can install as per the following command

    $ brew install python3 --with-brewed-openssl --framework --universal

This would also install pip3

Make sure system python points to brewed one.

    $ ls -la /System/Library/Frameworks/Python.framework/Versions/Current
    lrwxr-xr-x  1 root  wheel  3 Nov 16 22:00 /System/Library/Frameworks/Python.framework/Versions/Current -> 2.7
    $ sudo rm -rf /System/Library/Frameworks/Python.framework/Versions/Current
    $ sudo ln -sf /usr/local/Cellar/python/2.7.4/Frameworks/Python.framework/Versions/Current /System/Library/Frameworks/Python.framework/Versions/Current 
    $ ls -la /System/Library/Frameworks/Python.framework/Versions/Current
    lrwxr-xr-x  1 root  wheel  75 May  6 11:25 /System/Library/Frameworks/Python.framework/Versions/Current -> /usr/local/Cellar/python/2.7.4/Frameworks/Python.framework/Versions/Current

distribute
===
    $ pip install --upgrade distribute

[pip] [pip]
[pip]: http://en.wikipedia.org/wiki/Pip_(Python)
===
    $ pip install --upgarde pip

Following entry in your .bash_profile would help you cache pip downloads.

    # Define pip cache dir
    if [ -d ~/.pip/cache ]; then
        export PIP_DOWNLOAD_CACHE=~/.pip/cache
    fi

virtualenv
===
    $ pip install virtualenv
    $ pip install virtualenvwrapper

Sourcing virtualenvwrapper.sh gives you need scripts in ~/.virtualenvs/, you would like to add this to your .bash_profile as it defines some functions.

    $ source /usr/local/share/python/virtualenvwrapper.sh

    ~/.virtualenvs/
    ├── get_env_details
    ├── initialize
    ├── postactivate
    ├── postdeactivate
    ├── postmkproject
    ├── postmkvirtualenv
    ├── postrmproject
    ├── postrmvirtualenv
    ├── preactivate
    ├── predeactivate
    ├── premkproject
    ├── premkvirtualenv
    ├── prermproject
    └── prermvirtualenv
    
    0 directories, 14 files

Install [Fortran](http://en.wikipedia.org/wiki/Fortran)
===

    $ brew install gfortran

Install [NumPy](http://en.wikipedia.org/wiki/NumPy), [SciPy](http://en.wikipedia.org/wiki/SciPy) and [SymPy](http://en.wikipedia.org/wiki/SymPy)
===

These may give you a lot of warnings but would install.

    $ pip install numpy
    $ pip install scipy
    $ pip install sympy

Install [nltk](http://en.wikipedia.org/wiki/Natural_Language_Toolkit) (Natural language toolkit)
===

    $ pip install nltk

Install [matplotlib](http://en.wikipedia.org/wiki/Matplotlib)
===
http://matplotlib.org/
Tutorial: http://www.loria.fr/~rougier/teaching/matplotlib/

    $ pip install matplotlib

Install [IPython](http://en.wikipedia.org/wiki/IPython)
===

    $ pip install ipython

Install QT 
===

    $ brew install qt

Install pyqt
===

    $ brew install pyqt

Install ZeroMQ
===

    $ brew install zmq
    $ pip install pyzmq

Install pygments (Python syntax highlighter)
===

    $ pip install pygments

Starting IPython QTConsole
===

    ipython qtconsole --pylab=inline

If you need IPython Notebook

Install Tornado
===

    $ pip install tornado

Starting IPython Notebook
===

    $ ipython notebook --pylab=inline

Location for Notebooks
===

    $ alias ipn="cd ~/notebooks/; ipython notebook --pylab=inline"

---

References
===

http://www.thisisthegreenroom.com/2011/installing-python-numpy-scipy-matplotlib-and-ipython-on-lion/
http://hackercodex.com/guide/python-virtualenv-on-mac-osx-mountain-lion-10.8/
http://www.calvinx.com/2012/11/03/ipython-qtconsole/
https://github.com/sympy/sympy/wiki/Installing-the-IPython-qtconsole-in-Mac-OS-X
http://qt-project.org/downloads

Caveats
===

    ==> Caveats
    Brews that require a Fortran compiler should not use:
      depends_on 'gfortran'
    
    The preferred method of declaring Fortran support is to use:
      def install
        ...
        ENV.fortran
        ...
      end
    
    ==> Summary

---

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


---


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

