import 'dart:convert';
import 'dart:io';
import 'package:chewie/chewie.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import '../widgets/my_appbar.dart';
import 'package:webview_flutter/webview_flutter.dart';

class VideoScreen extends StatefulWidget {
  final String videoId;

  VideoScreen(this.videoId);

  @override
  _VideoScreenState createState() => _VideoScreenState();
}

class _VideoScreenState extends State<VideoScreen> {
  bool showLoading = true;

  bool showWeb = false;
  bool webResourceError = false;

  WebViewController _webViewController;

  VideoPlayerController _videoPlayerController;
  ChewieController _chewieController;

  @override
  void initState() {
    super.initState();
    initializePlayer();
  }

  Future<Map<String, Object>> fetch(String videoId) async {
    final url = "https://www.youtube.com/get_video_info?video_id=${videoId}";
    var request = await HttpClient().getUrl(Uri.parse(url));
    var response = await request.close();
    var content = '';

    await for (var s in response.transform(utf8.decoder)) {
      content += s;
    }
    content = Uri.decodeFull(content);
    var query = splitQueryString(content);
    Map<String, Object> jsonQuery = json.decode(query["player_response"]);
    // jsonQuery.forEach((key, value) {
    //   print(key);
    // });
    // print('---------------');
    Map<String, Object> streamingData = jsonQuery['streamingData'];
    // streamingData.forEach((key, value) {
    //   print(key);
    // });
    // print('---------------');
    List<dynamic> formats = streamingData['formats'];
    Map<String, Object> video = formats[0];
    return video;
    // video.forEach((key, value) {
    //   print(key);
    // });
    // print('---------------');
    // formats.forEach((element) {
    //   print(element['url']);
    //   print(element['quality']);
    // });
  }

  Map<String, Object> splitQueryString(String query) {
    return query.split('&').fold({}, (map, element) {
      final index = element.indexOf("=");
      if (index == -1) {
        if (element != "") {
          map[element] = "";
        }
      } else if (index != 0) {
        var key = element.substring(0, index);
        var value = element.substring(index + 1);
        map[key] = value;
      }
      return map;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: buildAppBar(context, "Video"),
      body: Container(
        width: MediaQuery.of(context).size.width,
        margin: EdgeInsets.symmetric(vertical: 8),
        child: _build_video(),
      ),
    );
  }

  Widget _build_video() {
    if (showLoading) {
      return Container(
        color: Colors.black,
        child: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }
    if(showWeb){
      if(!webResourceError){
        return WebView(
          javascriptMode: JavascriptMode.unrestricted,
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
                  
                  function onYouTubeIframeAPIReady() {
                      player = new YT.Player('player', {
                          videoId: '${widget.videoId}',
                          enablejsapi:1,
                          controls:1,
                          fs: 1,
                          events: {
                              onReady: function (event) {
                                  event.target.playVideo();
                              }
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
          initialMediaPlaybackPolicy:
          AutoMediaPlaybackPolicy.always_allow,
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
        );
      } else {
        return  Container(
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
                          // connectionStatus.refresh();
                          webResourceError = false;
                          showLoading = true;
                        });
                      },
                      child: Row(
                        children: [
                          Text("Refresh"),
                          Icon(Icons.refresh)
                        ],
                      )),
                  Expanded(child: Container())
                ],
              )
            ],
          ),
        );
      }
    }

    return Chewie(
      controller: _chewieController,
    );
  }

  Future<void> initializePlayer() async {
    try{
      var video = await fetch(widget.videoId);
      _videoPlayerController = VideoPlayerController.network(video['url']);
      await _videoPlayerController.initialize();
      _chewieController = ChewieController(
        videoPlayerController: _videoPlayerController,
        autoPlay: true,
        // looping: ture,
        // materialProgressColors: ChewieProgressColors(
        //   playedColor: Colors.red,
        //   handleColor: Colors.blue,
        //   backgroundColor: Colors.grey,
        //   bufferedColor: Colors.lightGreen,
        // ),
        // placeholder: Container(
        //   color: Colors.grey,
        // ),
        // autoInitialize: true,
      );
      setState(() {
        showLoading = false;
      });
    } catch(e){
      setState(() {
        showWeb = true;
      });
    }

  }

  @override
  void dispose() {
    _videoPlayerController?.dispose();
    _chewieController?.dispose();
    super.dispose();
  }
}
