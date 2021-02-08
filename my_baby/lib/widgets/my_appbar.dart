import 'package:flutter/material.dart';
AppBar buildAppBar(BuildContext context, title){
  final theme = Theme.of(context);
  return AppBar(
    backgroundColor: theme.scaffoldBackgroundColor,
    elevation: 0,
    title: Text(
      title,
      style:
      TextStyle(fontWeight: FontWeight.w500, color: Color(0xff364861)),
    ),
    centerTitle: true,
    leading: IconButton(
      icon: Icon(
        Icons.arrow_back_ios,
        color: theme.primaryColor,
        size: 20,
      ),
      onPressed: () {
        Navigator.of(context).pop();
      },
    ),
  );
}
