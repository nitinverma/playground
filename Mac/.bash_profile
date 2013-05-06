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

# Souring python virtual env
if [ -f /usr/local/share/python/virtualenvwrapper.sh ]; then
	source /usr/local/share/python/virtualenvwrapper.sh
fi

# Alais
alias play="cd ~/Codes/git/github/nitinverma/playground/"
alias pins="cd ~/Codes/git/github/pinadoc/pinadoc"

echo "Welcome $USER"
