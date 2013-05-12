# MacPorts Installer addition on 2013-04-17_at_18:38:23: adding an appropriate PATH variable for use with MacPorts.
# export PATH=/opt/local/bin:/opt/local/sbin:$PATH
# Finished adapting your PATH environment variable for use with MacPorts.

# set -x

# Read the current path into an array
# http://www.thegeekstuff.com/2010/06/bash-array-tutorial/
# if then else elif fi
# http://www.thegeekstuff.com/2010/06/bash-if-statement-examples/
export ORIG_PATH=$PATH

APATH=($(echo $PATH | awk -F: '{ for (i = 1; i <= NF; i++ ) { print $i} }' ))
BPATH=()

## ${USER}/bin

USER_BIN=${HOME}/bin

if [ -d $USER_BIN ]; then
	BPATH=("${BPATH[@]}" $USER_BIN)
fi

## Homebew installs

BREW=$(which brew 2>/dev/null)
BREW_PREFIX=$($BREW --prefix)

if [ -s $BREW ]; then
	BREW_BIN=${BREW_PREFIX}/bin
	BPATH=("${BPATH[@]}" $BREW_BIN)
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

if [ -d ~/Codes/git/github/pinadoc/pinadoc ]; then
	alias pins="cd ~/Codes/git/github/pinadoc/pinadoc"
fi

if [ -d ~/notebooks/ ]; then
	alias ipn="cd ~/notebooks/; ipython notebook --pylab=inline"
fi

alias sf="ssh nitin_matrix,pjam@shell.sourceforge.net"

echo "Alias list:"
alias
echo "PATH: $PATH"

echo "Welcome $USER"
