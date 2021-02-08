import 'package:flutter/material.dart';
import 'package:flutter/painting.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:flutter_html/style.dart';
import 'package:url_launcher/url_launcher.dart';

class HtmlWidget extends StatelessWidget {
  final String html;

  HtmlWidget(this.html);

  @override
  Widget build(BuildContext context) {
    return Html(
      data: html,
      style: {
        "html": Style(margin: EdgeInsets.all(0), padding: EdgeInsets.all(0)),
        "body": Style(
          margin: EdgeInsets.all(0),
          padding: EdgeInsets.all(0),
          // textAlign: TextAlign.justify,
        ),
        "ul": Style(fontSize: FontSize(18)),
        "ul span": Style(fontSize: FontSize(15)),
      },
      onLinkTap: (url) {
        launchUrl(url);
      },
    );
  }

  Future<void> launchUrl(String url) async {
    if (await canLaunch(url)) {
      await launch(url,
          forceWebView: true, enableJavaScript: true, forceSafariVC: false);
    }
  }
}
