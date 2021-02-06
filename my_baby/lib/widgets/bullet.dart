import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:my_baby/models/resource.dart';
import 'package:my_baby/widgets/html_widget.dart';

class Bullet extends StatelessWidget {
  final Object text;

  Bullet({this.text});

  @override
  Widget build(BuildContext context) {
    Widget child;
    if (text is String)
      child = Text(text);
    else
      child = HtmlWidget('${(text as HtmlText).text}');
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text('•\t', style: TextStyle(fontSize: 18),),
        Flexible(child: child),
      ],
    );
  }
}
