#!/bin/bash
#
# Terrier - Terabyte Retriever
# Webpage: http://terrier.org
# Contact: terrier@dcs.gla.ac.uk 
#
# The contents of this file are subject to the Mozilla Public
# License Version 1.1 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a copy of
# the License at http://www.mozilla.org/MPL/
#
# Software distributed under the License is distributed on an "AS
# IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
# implied. See the License for the specific language governing
# rights and limitations under the License.
#
# The Original Code is http_server.sh
#
# The Initial Developer of the Original Code is the University of Glasgow.
# Portions created by The Initial Developer are Copyright (C) 2004-2011
# the initial Developer. All Rights Reserved.
#
# Contributor(s):
#	Vassilis Plachouras <vassilis@dcs.gla.ac.uk> (original author)
#	Craig Macdonald <craigm@dcs.gla.ac.uk>
#
# a script for starting a Jetty webserver, for retrieval results
# by default, port 8080

fullPath () {
	t='TEMP=`cd $TEMP; pwd`'
	for d in $*; do
		eval `echo $t | sed 's/TEMP/'$d'/g'`
	done
}

TERRIER_BIN=`dirname $0`
if [ -e "$TERRIER_BIN/terrier-env.sh" ];
then
	. $TERRIER_BIN/terrier-env.sh
fi

#setup TERRIER_HOME
if [ ! -n "$TERRIER_HOME" ]
then
	#find out where this script is running
	TEMPVAR=`dirname $0`
	#make the path abolute
	fullPath TEMPVAR
	#terrier folder is folder above
	TERRIER_HOME=`dirname $TEMPVAR`
	#echo "Setting TERRIER_HOME to $TERRIER_HOME"
fi

if [ -z "$TERRIER_LIB" ];
then
	TERRIER_LIB=$TERRIER_HOME/lib/
fi

for f in $TERRIER_LIB/jetty-ext/*.jar;
do
	if [ -z "$CLASSPATH" ]
	then
		CLASSPATH=$f
	else
		CLASSPATH=$CLASSPATH:$f
	fi
done

export CLASSPATH

if [ "$#" == "0" ];
then
	$TERRIER_HOME/bin/anyclass.sh org.terrier.utility.SimpleJettyHTTPServer 8080 $TERRIER_HOME/src/webapps/simple/
else
	$TERRIER_HOME/bin/anyclass.sh org.terrier.utility.SimpleJettyHTTPServer $*
fi
