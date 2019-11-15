package api;


/**
 * Basic API for testing the functionality of the STS manager
 */
public interface IDivvyTripsManager {
	
	
	/**
	 * Method to load the Divvy Stations of the STS
	 * @param stationsFile - path to the file 
	 */
	void loadStations(String stationsFile);

	public void loadIntersection (String IntersectionFile);
	
	public void loadEdges (String edgesFile);


	
}
