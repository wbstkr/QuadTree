# Spatial QuadTree Simulation

> A high-performance 2D spatial partitioning data structure designed to optimize entity queries and collision detection, featuring a real-time algorithmic benchmark.

## Algorithmic Logic

In standard 2D physics engines, checking for collisions between moving entities requires checking every single entity against every other entity, resulting in a highly inefficient O(n²) time complexity. 

This project resolves this bottleneck by implementing a **QuadTree**:

*   **Spatial Partitioning:** The algorithm dynamically divides the 2D space into four equal quadrants whenever a specific spatial boundary reaches its maximum capacity.
*   **Optimized Queries:** Instead of checking every entity globally, the system only processes collision logic for entities existing within the same localized quadrant.
*   **Real-Time Benchmark:** The simulation includes a live performance benchmark. Pressing the `Spacebar` toggles the engine between the optimized QuadTree (O(n log n)) and a naive nested loop (O(n²)), demonstrating a massive, real-time drop in frame rate under heavy entity loads.

## Preview

<img width="602" height="628" alt="QuadTree Preview" src="https://github.com/user-attachments/assets/4aebc8af-468a-4248-bee9-94b600a40c15" />

## Getting Started

To run this simulation locally:

1.  Clone the repository.
2.  Open the project in your preferred JVM IDE.
3.  Execute `Main.kt` to initialize the spatial simulation.
4.  Press `Spacebar` to toggle between the QuadTree and Naive benchmark modes.

---

## Resources
*   [Quadtree - Wikipedia](https://en.wikipedia.org/wiki/Quadtree): Helpful foundational literature on spatial partitioning mathematics and node-based data structures used to design the core algorithm.
