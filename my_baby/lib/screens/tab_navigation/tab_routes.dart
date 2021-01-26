import 'package:flutter/material.dart';
import '../resources/resource_content_screen.dart';
import '../profile/profile_screen.dart';
import '../health/health_screen.dart';
import '../timeline/time_line_screen.dart';
import '../resources/resource_subsections_screen.dart';
import '../resources/resources_screen.dart';

final Map<String, WidgetBuilder> timelineRoutes = {
  TimeLineScreen.route: (context) => TimeLineScreen()
};

final Map<String, WidgetBuilder> healthRoutes = {
  TimeLineScreen.route: (context) => HealthScreen()
};

final Map<String, WidgetBuilder> resourceRoutes = {
  ResourcesScreen.route: (context) => ResourcesScreen(),
  ResourceSubsectionsScreen.route: (context) => ResourceSubsectionsScreen(),
  ResourceContentScreen.route : (context) => ResourceContentScreen()
};

final Map<String, WidgetBuilder> profileRoutes = {
  TimeLineScreen.route: (context) => ProfileScreen()
};
