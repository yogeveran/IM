@ECHO OFF
setlocal EnableDelayedExpansion
::========================================= epsilon 0.2
ECHO epsilon=0.2,alpha=0.2 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.2,alpha=0.2 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)

ECHO epsilon=0.2,alpha=0.5 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.2,alpha=0.5 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)

ECHO epsilon=0.2,alpha=0.8 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.2,alpha=0.8 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)
::========================================= epsilon 0.5
ECHO epsilon=0.5,alpha=0.2 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.5,alpha=0.2 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)

ECHO epsilon=0.5,alpha=0.5 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.5,alpha=0.5 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)

ECHO epsilon=0.5,alpha=0.8 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.5,alpha=0.8 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)
::========================================= epsilon 0.8
ECHO epsilon=0.8,alpha=0.2 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.5,alpha=0.2 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)

ECHO epsilon=0.8,alpha=0.5 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.5,alpha=0.5 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)

ECHO epsilon=0.8,alpha=0.8 >> AllAproxResults.txt
FOR /L %%N IN (1,2,10) DO (
	set /a M = %%N + 3
	ECHO Training %%N  >> AllAproxResults.txt
	python pacman.py -p ApproximateQAgent -a extractor=SimpleExtractor,epsilon=0.5,alpha=0.8 -x %%N -n !M! -l mediumClassic |findstr "Win Rate" >> AllAproxResults.txt
)
