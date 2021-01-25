import 'package:flutter/material.dart';

class HealthScreen extends StatelessWidget {
  static const route = '/';
  @override
  Widget build(BuildContext context) {
    print("health");
    return Center(
      child: Container(
        child: Text(
          "Health",
        ),
      ),
    );
  }
}
