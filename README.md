# Engage News App

### Tech and Architecture:
* This application takes advantage of MVVM architecture
* Activity, ViewModel, Repository, LiveData, ObservableFields, DataBinding are the basic building blocks of this architecture
* All the UI related work like showing Toast, starting a new activity, showing the progress bar, etc is done in the Activity. 
* Business logic is written in the ViewModel. Also the observable fields are present in the ViewModel which are observed from the XML when we use data binding. 
* All API and DB calls happen from within the repository.
* It's a 1 way reference flow from Activity -> View Model -> Repository. For ViewModel/Repository to communicate back in the chain we use live data which is observed by the concerned entity.
* We have used Java as the primary programming language
* Constraint Layout has been used to design the UI

### Libraries:
Apart from Gson(), no other 3rd party library has been used. Everything is built on top of the System APIs

### App screenshots:
![Image description](https://github.com/rohegde7/Moengage-Android-NewsApp/blob/master/app%20screenshorts/Screenshot%202020-03-29%20at%206.58.28%20PM.png)
![Image description](https://github.com/rohegde7/Moengage-Android-NewsApp/blob/master/app%20screenshorts/Screenshot%202020-03-29%20at%206.56.15%20PM.png)
