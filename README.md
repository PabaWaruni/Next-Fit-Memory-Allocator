# Next-Fit-Memory-Allocator

The projects implement a Next Fit Algorithm with a Graphical User Interface (GUI) using Java. The Next Fit is upgraded version of First Fit algorithm. It begins as the first fit to find a free partition but when called next time it starts searching from where it left off, not from the beginning.

## Gaphical User Interface (GUI) and Functionalities

- The application initializes the memory pool with predefined block sizes for demonstration purposes ({100, 50, 200, 300}). <br/>
- Indicates the current block pointer ([Current Block]) for the Next Fit algorithm. <br/>
- Allocates the smallest block that satisfies the process size requirement to reduce fragmentation. <br/>
- If the allocated block size is larger than the process size, the remaining memory is split into a new free block. <br/>
- Users can deallocate memory by specifying the index of an allocated block. <br/>
- Marks the block as free, allowing it to be reused for future allocations. <br/>

![Screenshot 2024-12-14 140113](https://github.com/user-attachments/assets/e11d8458-1dd9-4123-89f3-2f78cf53b40f)

![Screenshot 2024-12-14 140155](https://github.com/user-attachments/assets/26dd8cca-524f-4c25-bd6a-cc8c68ce787e)

![Screenshot 2024-12-14 172409](https://github.com/user-attachments/assets/a5fefaae-3b78-43e9-8215-57b2bf07989c)
