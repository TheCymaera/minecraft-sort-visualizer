CURRENT_DIRECTORY=$(dirname "$BASH_SOURCE")

echo =====================================
echo CD-ing into current directory...
cd $CURRENT_DIRECTORY
echo $PWD
echo =====================================
echo Creating new symlinks...
if [ ! -d ../../paper-server/world ]; then
	ln -s $PWD/world ../../paper-server/world
fi
ln -s $PWD/target/Plugin-1.0-SNAPSHOT.jar ../../paper-server/plugins/dev.jar
echo =====================================
