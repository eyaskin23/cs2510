

//           +----------+
//           | AppSet   |
//           +----------+
//           +----------+
//                 |
//                / \
//                ---
//                 |
//       -----------------------
//       |                     |
//+--------------+    +-----------------+
//| Folder       |    | Apps            |
//+--------------+    +-----------------+
//| String       |    | app-name        |
//|              |    | others(appset)  |
//+--------------+    +-----------------+

//represents a group of apps
interface IAppSet {
}

//represents a folder of apps
class Folder implements IAppSet {
  String title;
  
  Folder(String title) {
    this.title = title;
  }
}

//represents apps
class Apps implements IAppSet {
  String appName;
  IAppSet others;

  Apps(String appName, IAppSet others) {
    this.appName = appName;
    this.others = others;
  }
}

//Represents examples for the class Apps
class ExamplesSets {
  IAppSet travelFolder = new Folder("Travel");
  IAppSet travelApp1 = new Apps("Uber", this.travelFolder);
  IAppSet travelApp2 = new Apps("mTicket", this.travelApp1);
  IAppSet travelApp3 = new Apps("Moovit", this.travelApp2);
  IAppSet travelApps = new Apps("Orbitz", this.travelApp3);

  IAppSet foodFolder = new Folder("Food");
  IAppSet foodApp1 = new Apps("Grubhub", this.foodFolder);
  IAppSet foodApp2 = new Apps("B. Good", this.foodApp1);
  IAppSet foodApps = new Apps("Gong Cha", this.foodApp2);
}




