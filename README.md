AndroidRMI
==========
This project contains a server and a Android client.
- By using Java RMI, client side can offload task to remote server side.
- Users can decide the difficulty of the task.
- Users can test the network speed, battery, running time.
- Graphs showing relationship between runtime and network speed can be shown for both local and remote.


How to run
-----------
1. Run RMIServer
2. Get IP for RMIServer, and assign it to variable remoteHost in Utils.java in client side
3. Run AndroidRMI

Demo
------
- Main activity to test:

![Image Alt](https://github.com/skyw932/AndroidRMI/raw/master/Demo/1.png)

- Input difficulty for computer:

![Image Alt](https://github.com/skyw932/AndroidRMI/raw/master/Demo/2.png)

- Get back result as toast:

![Image Alt](https://github.com/skyw932/AndroidRMI/raw/master/Demo/3.png)

- Graph activity to show network speed vs run time for both local and remotr function:
- 
![Image Alt](https://github.com/skyw932/AndroidRMI/raw/master/Demo/4.png)
