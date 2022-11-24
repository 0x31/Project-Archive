X="SET-REPO-HERE"

# Prepare repo
cd ../$X
mkdir $X
mv ./* $X
mv ./.* $X
mv $X/.git ./
git add --all
git commit -m "Prepared $X to be merged into Project-Archive"

# Merge repo into Project-Archve
cd ../Project-Archive
git remote add $X ../$X
git fetch $X
git merge --no-edit --allow-unrelated-histories $X/master

# Move repo into done directory
mv ../$X ../done