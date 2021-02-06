import 'dart:convert';
import 'package:flutter/material.dart';
import '../widgets/my_appbar.dart';
import 'package:webview_flutter/webview_flutter.dart';

class VideoScreen extends StatefulWidget {
  final String videoId;

  VideoScreen(this.videoId);

  @override
  _VideoScreenState createState() => _VideoScreenState();
}

class _VideoScreenState extends State<VideoScreen> {
  bool showLoading = false;
  bool webResourceError = false;

  WebViewController _webViewController;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: buildAppBar(context, "Video"),
      body: Container(
        width: MediaQuery.of(context).size.width,
        margin: EdgeInsets.symmetric(vertical: 8),
        child: Stack(
          children: [
            if (!webResourceError)
              WebView(
                javascriptMode: JavascriptMode.unrestricted,
                initialMediaPlaybackPolicy: AutoMediaPlaybackPolicy.always_allow,
                javascriptChannels: Set.from([
                JavascriptChannel(
                  name: "errorChannel",
                  onMessageReceived: (message){
                    print(message.message);
                    setState(() {
                      showLoading = false;
                      webResourceError = true;
                    });
                  }
                )
                ]),
                onWebViewCreated: (controller) {
                  String html = '''
                 <!DOCTYPE html>
                 <html lang="en">
                 <style>
                  iframe {
                      position: absolute;
                      top: 0; 
                      left: 0;
                      border: 0;
                      width: 100%;
                      height: 100%;
                  }
                 </style>
                 <body> 
                 <div id="player"></div>
                 <script>
                  var tag = document.createElement('script');
                  tag.src = "https://www.youtube.com/iframe_api";
                  var firstScriptTag = document.getElementsByTagName('script')[0];
                  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
                  var player;
                  var onError = function(event) {
                     errorChannel.postMessage("error");  // channel handle error
                  }
                  
                  function onYouTubeIframeAPIReady() {
                      player = new YT.Player('player', {
                          videoId: '${widget.videoId}',
                          enablejsapi:1,
                          controls:1,
                          fs: 1,
                          events: {
                              onReady: function (event) {
                                  event.target.playVideo();
                              },
                              onError: onError
                          }
                      });
                  }
                  
                </script>
                </body>
                </html>
                ''';
                  final String contentBase64 =
                      base64Encode(const Utf8Encoder().convert(html));
                  controller.loadUrl('data:text/html;base64,$contentBase64');
                  setState(() {
                    showLoading = true;
                  });
                  _webViewController = controller;
                },
                onPageFinished: (val) {
                  setState(() {
                    showLoading = false;
                  });
                },
                onWebResourceError: (e) {
                  setState(() {
                    webResourceError = true;
                  });
                },
              )
            else
              Container(
                color: Colors.grey.shade200,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Container(
                          width: 200,
                          child: Text(
                            "Error in Loading video, please check internet connection",
                            style: TextStyle(fontWeight: FontWeight.w600),
                            textAlign: TextAlign.center,
                          ),
                        ),
                        SizedBox(
                          width: 5,
                        ),
                        Icon(
                          Icons.error,
                          color: Colors.red,
                        )
                      ],
                    ),
                    Row(
                      children: [
                        Expanded(child: Container()),
                        RaisedButton(
                            onPressed: () {
                              setState(() {
                                webResourceError = false;
                                showLoading = true;
                              });
                            },
                            child: Row(
                              children: [Text("Refresh"), Icon(Icons.refresh)],
                            )),
                        Expanded(child: Container())
                      ],
                    )
                  ],
                ),
              ),
            if (showLoading)
              Container(
                color: Colors.black,
                child: Center(
                  child: CircularProgressIndicator(),
                ),
              )
          ],
        ),
      ),
    );
  }
}
