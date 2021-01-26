import 'package:flutter/material.dart';

import '../health/health_screen.dart';
import '../profile/profile_screen.dart';
import '../timeline/time_line_screen.dart';
import '../resources/resources_screen.dart';
import 'tab_routes.dart';

enum TabItem {
  TIMELINE,
  HEALTH,
  RESOURCES,
  PROFILE
}

class Tab {
  final String name;
  final Icon icon;
  final Map<String, WidgetBuilder> routes;
  final String initialRoute;

  Tab({this.name, this.icon, this.routes, this.initialRoute});
}

final Map<TabItem, Tab> tabs = {
  TabItem.TIMELINE: Tab(
      name: "Timeline",
      icon: Icon(Icons.view_list),
      routes: timelineRoutes,
      initialRoute: TimeLineScreen.route
  ),
  TabItem.HEALTH: Tab(
      name: "Health",
      icon: Icon(Icons.favorite),
      routes: healthRoutes,
      initialRoute: HealthScreen.route
  ),
  TabItem.RESOURCES: Tab(
      name: "Resources",
      icon: Icon(Icons.folder),
      routes: resourceRoutes,
      initialRoute: ResourcesScreen.route
  ),
  TabItem.PROFILE: Tab(
    name: "Profile",
    icon:  Icon(Icons.person),
    routes:  profileRoutes,
    initialRoute: ProfileScreen.route
  )
};

