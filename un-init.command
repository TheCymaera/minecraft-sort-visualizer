CURRENT_DIRECTORY=$(dirname "$BASH_SOURCE")

echo =====================================
echo CD-ing into current directory...
cd $CURRENT_DIRECTORY
echo $PWD
echo =====================================
echo Removing symlinks...
rm  ../../paper-server/world
rm ../../paper-server/plugins/dev.jar
echo =====================================
