class Solution {
    /**
     * Finds the K closest points to the origin (0, 0) in the given array of points.
     * @param points 2D array where each inner array represents a point [x, y]
     * @param K number of closest points to find
     * @return 2D array of K closest points to the origin
     */
    public int[][] kClosest(int[][] points, int K) {
        int pointsLen = points.length;
        // distances[i][0] is squared distance, distances[i][1] is index in points
        int[][] distances = new int[pointsLen][2];
        
        // Calculate squared distances and store original indices
        for (int i = 0; i < pointsLen; i++) {
            distances[i][0] = getSquaredDistance(points[i]);
            distances[i][1] = i;
        }
        
        // Use QuickSelect to partition the array so that the K closest points are at the beginning
        quickSelect(distances, 0, pointsLen - 1, K);
        
        // Collect the K closest points
        int[][] result = new int[K][2];
        for (int i = 0; i < K; i++) {
            result[i] = points[distances[i][1]];
        }
        
        return result;
    }
    
    /**
     * QuickSelect algorithm to partition the array so that the K smallest elements are at the beginning.
     * This version uses the middle element as pivot and incorporates the partitioning logic.
     * @param distances array of [squared distance, original index] pairs
     * @param start start index of the current partition
     * @param end end index of the current partition
     * @param K number of smallest elements to partition
     */
    private void quickSelect(int[][] distances, int start, int end, int K) {
        while (start < end) {
            // Choose middle element as pivot
            int mid = start + (end - start) / 2;
            int pivot = distances[mid][0];
            
            // Move pivot to end
            swap(distances, mid, end);
            
            // Partitioning
            int separatorIndex = start;
            for (int j = start; j < end; j++) {
                if (distances[j][0] < pivot) {
                    swap(distances, separatorIndex, j);
                    separatorIndex++;
                }
            }
            
            // Move pivot to its final position
            swap(distances, separatorIndex, end);
            
            // Adjust search range based on separator's position
            if (separatorIndex == K - 1) {
                // We've found the Kth element, so we're done
                return;
            } else if (separatorIndex < K - 1) {
                // K is in the right partition
                start = separatorIndex + 1;
            } else {
                // K is in the left partition
                end = separatorIndex - 1;
            }
        }
    }
    
    /**
     * Calculates the squared Euclidean distance of a point from the origin.
     * We use squared distance to avoid floating point imprecision and unnecessary sqrt operations.
     * @param point array representing a 2D point [x, y]
     * @return squared distance from origin
     */
    private int getSquaredDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }
    
    /**
     * Swaps two elements in the given array.
     * @param arr the array in which to perform the swap
     * @param i index of the first element
     * @param j index of the second element
     */
    private void swap(int[][] arr, int i, int j) {
        int[] temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
