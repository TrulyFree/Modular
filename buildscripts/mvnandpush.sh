#!/bin/bash

# Modular library by TrulyFree: A general-use module-building library.
# Copyright (C) 2016  VTCAKAVSMoACE
# 
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
JAVADOCS_PROMPT="Build javadocs? [Y/n] "
MVN_OPTIONS_PROMPT="Maven arguments? "
MSG="$1"
BRANCH="$2"
PUSH="$3"

cd "$TARGET_DIR"

echo -n "$JAVADOCS_PROMPT"
read javadocs

echo -n "$MVN_OPTIONS_PROMPT"
read mvn_opt

mvn clean

if [[ $javadocs =~ ^[Yy]$ ]]
then
	mvn javadoc:javadoc
	mvn javadoc:test-javadoc
fi

mvn $mvn_opt
mvn surefire-report:report-only
mvn site -DgenerateReports=false

git show-ref --verify --quiet refs/heads/"$BRANCH"
if [[ $? -ne 0 ]]; then
	git checkout -b "$BRANCH"
else
	git checkout "$BRANCH"
fi

git add -A
git commit -m "$MSG"

if [[ $PUSH =~ ^[Yy]$ ]]
then
	git push origin "$BRANCH"
fi