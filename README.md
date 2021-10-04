# concurrency_control

This is a concurrency library that is built primary for files but extendible to any kind of shared data objects.
This enables multiple distributed applications to work on shared data without worrying about concurrency issues and without any communication overhead.  
Multithreaded concurrency issues within same application is resolved through local locks and consistency of data from remote set of applications is ensured by db-based locking mechanism.

