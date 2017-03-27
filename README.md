# PersistentBTree
A project for one of my classes at SUNY Oswego. The main focus of the project was to build a persistent BTree.

The project is divided up into several main structures
## BTree
The BTree consists of persistent nodes, each of which holds some number of keys based on the order of the
tree (default: 100). The keys loaded in for my project are Strings corresponding to a city, month and year.
These keys correspond to the average temperature for the month in that city, though the actual temperatures
are stored in a separate data structure, a Byte stream. The values in the BTree are actually offsets which
tell where in the Byte stream the temperature value (a double) is located.

## Byte Stream
The byte stream is simply a file in which doubles representing temperature value are listed as bytes sequentially.
The offset in the stream a given temperature value is stored at can be found by looking up the temperature's
corresponding key in the BTree.

## Data Cache
The data for this assignment is pulled from the NOAA Web Service for weather data. When the program is first run,
the data from the web service is stored in a persistent hash map, and the BTree is then build based on that.
In subsequent runs, the responses from the web service are checked against the ones in the data cache. If no
differences are found, then the data in the persistent BTree is up-to-date, so the BTree does not have to be rebuilt.
Since building the persistent BTree takes a long time (over 10 seconds), this speeds up the initialization process of
the program greatly.

## Clusters
The keys for the data are organized into clusters during BTree creation. I hand picked the clusters by choosing
several temperatures, and inserting each key into the cluster with the nearest temperature.

## Interface
At run time, a key is selected by the user by selecting a city, month and year. There are three buttons which will
calculate results based on this input. Value returns the average temperature in the selected city over the course of
the selected month. Cluster returns the cluster the key belongs to, as well as how many keys are in that cluster,
and a list of keys in the cluster. Similarity takes a moment for its calculation (as it traverses the entire BTree)
but it looks at the selected city, then calculates the most similar city based on the differences in average temperature
each month across all the data (in my case, every month from 2006 to 2015).
