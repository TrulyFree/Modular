#!/bin/bash

TARGET_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/.."
MVN_OPTIONS_PROMPT="Maven arguments? "
MSG_PROMPT="Commit message? "
BRANCH_PROMPT="Branch? "

cd "$TARGET_DIR"

echo -n "$MVN_OPTIONS_PROMPT"
read mvn_opt

mvn $mvn_opt

echo -n "$MSG_PROMPT"
read msg
echo -n "$BRANCH_PROMPT"
read branch

git show-ref --verify --quiet refs/heads/"$branch"
if [[ $? -ne 0 ]]; then
	git checkout -b "$branch"
fi

git add -A
git commit -m "$msg"
git push origin "$branch"