@ECHO OFF
setlocal EnableDelayedExpansion
::========================================= epsilon 0.2
ECHO epsilon=0.2,alpha=0.2 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.2,alpha=0.2 |findstr "Win Rate" >> AllResults.txt
)

ECHO epsilon=0.2,alpha=0.5 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.2,alpha=0.5 |findstr "Win Rate" >> AllResults.txt
)

ECHO epsilon=0.2,alpha=0.8 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.2,alpha=0.8 |findstr "Win Rate" >> AllResults.txt
)
::========================================= epsilon 0.5
ECHO epsilon=0.5,alpha=0.2 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.5,alpha=0.2 |findstr "Win Rate" >> AllResults.txt
)

ECHO epsilon=0.5,alpha=0.5 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.5,alpha=0.5 |findstr "Win Rate" >> AllResults.txt
)

ECHO epsilon=0.5,alpha=0.8 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.5,alpha=0.8 |findstr "Win Rate" >> AllResults.txt
)
::========================================= epsilon 0.8
ECHO epsilon=0.5,alpha=0.2 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.8,alpha=0.2 |findstr "Win Rate" >> AllResults.txt
)

ECHO epsilon=0.5,alpha=0.5 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.8,alpha=0.5 |findstr "Win Rate" >> AllResults.txt
)

ECHO epsilon=0.5,alpha=0.8 >> AllResults.txt
FOR /L %%N IN (250,250,2000) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllResults.txt
	python pacman.py -p PacmanQAgent -x %%N -n !M! -l smallGrid -a epsilon=0.8,alpha=0.8 |findstr "Win Rate" >> AllResults.txt
)