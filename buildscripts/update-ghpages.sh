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

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/../"
SITE_DIR="site/"
MSG="$1"
TEST_TYPE="$2"
PUSH="$3"
BRANCH="gh-pages"

cd "$TARGET_DIR"

rm -rv "$SITE_DIR""$TEST_TYPE""-tests"
rm -rv "$SITE_DIR""apidocs"
rm -rv "$SITE_DIR""testapidocs"

mkdir -p "$SITE_DIR""$TEST_TYPE""-tests"

cp -rv target/site/apidocs "$SITE_DIR"apidocs
cp -rv target/site/testapidocs "$SITE_DIR"testapidocs
cp -rv target/site/css target/site/images target/site/surefire-report.html "$SITE_DIR""$TEST_TYPE""-tests/"

cd "$SITE_DIR"
mv -v "$TEST_TYPE""-tests/"surefire-report.html "$TEST_TYPE""-tests/"index.html

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