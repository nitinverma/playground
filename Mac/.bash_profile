# MacPorts Installer addition on 2013-04-17_at_18:38:23: adding an appropriate PATH variable for use with MacPorts.
# export PATH=/opt/local/bin:/opt/local/sbin:$PATH
# Finished adapting your PATH environment variable for use with MacPorts.

# set -x

# Read the current path into an array
# http://www.thegeekstuff.com/2010/06/bash-array-tutorial/
# if then else elif fi
# http://www.thegeekstuff.com/2010/06/bash-if-statement-examples/

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_21.jdk/Contents/Home
export ORIG_PATH=$PATH
export RBENV_ROOT=/usr/local/var/rbenv
export PYTHONPATH="/usr/local/lib/python2.7/site-packages"

APATH=($(echo $PATH | awk -F: '{ for (i = 1; i <= NF; i++ ) { print $i} }' ))
BPATH=()

## ${USER}/bin

USER_BIN=${HOME}/bin
USER_LOCAL_BIN=${HOME}/usr/local/bin
USER_LOCAL_SBIN=${HOME}/usr/local/sbin
USER_REMOTE_BIN=${HOME}/usr/remote/bin

if [[ -d $RBENV_ROOT/shims ]]
then
	BPATH=("${BPATH[@]}" $USER_BIN)
fi

if [ -d $USER_BIN ]; then
	BPATH=("${BPATH[@]}" $USER_BIN)
fi

if [ -d $USER_LOCAL_BIN ]; then
	BPATH=("${BPATH[@]}" $USER_LOCAL_BIN)
fi

if [ -d $USER_LOCAL_SBIN ]; then
	BPATH=("${BPATH[@]}" $USER_LOCAL_SBIN)
fi

if [ -d $USER_REMOTE_BIN ]; then
	BPATH=("${BPATH[@]}" $USER_REMOTE_BIN)
fi
## Homebew installs

BREW=$(which brew 2>/dev/null)
BREW_PREFIX=$($BREW --prefix)

if [ -s $BREW ]; then
	BREW_BIN=${BREW_PREFIX}/bin
	BREW_SBIN=${BREW_PREFIX}/sbin
	BPATH=("${BPATH[@]}" $BREW_BIN $BREW_SBIN)
	PYTHON_SHARE=${BREW_PREFIX}/share/python
	BPATH=("${BPATH[@]}" $PYTHON_SHARE)
fi

# Remove dups
# keeping the highest index

for LOCATION in "${APATH[@]}"
do
	donotadd=0
	for BLOC in "${BPATH[@]}"
	do
		if [ ""$BLOC"" = ""$LOCATION"" ]; then
			donotadd=1
			break;
		fi
	done
	
	if [ $donotadd -eq 0 ]; then
		BPATH=("${BPATH[@]}" $LOCATION)
	fi

done

for index in "${!BPATH[@]}"
do
	if [ $index -eq 0 ]
	then
		PATH=${BPATH[$index]}
	else
		PATH="$PATH:${BPATH[$index]}"
	fi
done

export PATH

# Define pip cache dir
if [ -d ~/.pip/cache ]; then
	export PIP_DOWNLOAD_CACHE=~/.pip/cache
fi

# Souring python virtual env
if [ -f /usr/local/share/python/virtualenvwrapper.sh ]; then
	source /usr/local/share/python/virtualenvwrapper.sh
fi

# rbenv

RBENV=$(which rbenv 2>/dev/null)

if [ -s $RBENV ]
then
	eval "$(rbenv init -)"
fi

# Alias
MACVIM=$(which mvim 2>/dev/null)

if [ -d $USER_BIN ]; then
	alias my="cd $USER_BIN"
fi

if [ -s $MACVIM ]; then
	alias vi="$MACVIM"
fi

if [ -d ~/Codes/git/github/nitinverma/playground ]; then
	alias play="cd ~/Codes/git/github/nitinverma/playground/"
fi

if [ -d ~/notebooks/ ]; then
	alias ipn="cd ~/notebooks/; ipython notebook --pylab=inline"
fi

if [ -d ~/Library/Caches/Nitin ]; then
	alias myc="cd ~/Library/Caches/Nitin"
fi

alias of="open \${PWD}"
alias sf="ssh nitin_matrix,pjam@shell.sourceforge.net"
alias sfc="ssh -t nitin_matrix,pjam@shell.sourceforge.net create"
alias rediss="redis-server /usr/local/etc/redis.conf"

source ~/.pins.alias

echo "Alias list:"
alias
echo "PATH: $PATH"

echo "Welcome $USER"
