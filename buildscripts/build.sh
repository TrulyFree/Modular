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
MSG_PROMPT="Commit message? "
BRANCH_PROMPT="Code branch? "
GH_PAGES_PROMPT="Update gh-pages? [Y/n] "
PUSH_PROMPT="Push to remote? [Y/n] "

echo -n "$MSG_PROMPT"
read msg

echo -n "$BRANCH_PROMPT"
read branch

echo -n "$GH_PAGES_PROMPT"
read ghpages

echo -n "$PUSH_PROMPT"
read push

cd "$TARGET_DIR"

buildscripts/mvnandpush.sh "$msg" "$branch" "$push"

if [[ $ghpages =~ ^[Yy]$ ]]
then
	buildscripts/update-ghpages.sh "$msg" "$branch" "$push"
fi