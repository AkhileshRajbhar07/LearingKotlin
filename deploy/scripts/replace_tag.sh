#!/bin/bash
#
# Script to build multiple docker build targets at once
# Uses following environment variables if
# - DOCKER_BUILD_TARGETS - string with targets comma separated if not set will be ignored
if [[ -z "$1" ]]; then
	echo "No parameter for path provided. Usage: ./replace_tag.sh /some/path some_tag_to_replace"
	exit 1
fi

if [[ -z "$2" ]]; then
	echo "No parameter for tag to replace provided. Usage: ./replace_tag.sh /some/path some_tag_to_replace"
	exit 1
fi

echo "-------------------------------------------------------"
echo "Tags before change:"
grep -e tag: "$1"
echo ""
if [[ -z "$DOCKER_BUILD_TARGETS" ]]; then
	echo "Replace for target $item tag to \"$2\" in $1"
	sed -i -e "s/tag:.*/tag: \"$2\"/" "$1"
else
	# all env vars are set we can continue to build
	# split all variables https://stackoverflow.com/a/31405855
	mapfile -t build_targets < <(echo "$DOCKER_BUILD_TARGETS" | tr ',' '\n')
	for item in "${build_targets[@]}"; do
		echo "Replace for target $item tag to \"$2\" in $1"
		sed -i -e "s/tag:.*$item.*/tag: \"$item.$2\"/" "$1"
	done
fi

echo ""
echo "Resulting tags:"
grep -e tag: "$1"
echo "-------------------------------------------------------"
