import 'dart:convert';
import 'dart:io';

import 'package:chewie/chewie.dart';
import 'package:flutter/material.dart';
import 'package:my_baby/screens/video_screen.dart';
import 'package:my_baby/services/connection_status.dart';
import 'package:my_baby/widgets/custom_video_controls.dart';
import 'package:provider/provider.dart';
import 'package:video_player/video_player.dart';

class VideoWidget extends StatefulWidget {
  final String videoId;

  VideoWidget(this.videoId);

  @override
  _VideoWidgetState createState() => _VideoWidgetState();
}

class _VideoWidgetState extends State<VideoWidget> {
  VideoPlayerController _videoPlayerController;
  ChewieController _chewieController;
  bool isVideoInit = false;

  bool showLoading = false;

  bool inlineVideo = false;
  bool gettingVideoInfo = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final videoHeight = 192.0;
    return Container(
      margin: EdgeInsets.symmetric(vertical: 8),
      child: ChangeNotifierProvider(
        create: (_) => ConnectionStatus(),
        child: Consumer<ConnectionStatus>(
          builder: (context, connectionStatus, _) {
            if (connectionStatus.connecting)
              return Container(
                color: Colors.grey.shade200,
                child: Center(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        "Connecting to internet",
                        style: TextStyle(fontWeight: FontWeight.w600),
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      LinearProgressIndicator()
                    ],
                  ),
                ),
              );
            else if (connectionStatus.isConnectedFirstTime) {
              return Container(
                height: videoHeight,
                child: _buildPlayer(connectionStatus),
              );
            } else if (!connectionStatus.isConnected) {
              return Container(
                color: Colors.grey.shade200,
                child: Center(
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        "No Internet Connection",
                        style: TextStyle(fontWeight: FontWeight.w600),
                      ),
                      SizedBox(
                        width: 5,
                      ),
                      Icon(
                        Icons.error_outline,
                        color: Colors.red,
                      )
                    ],
                  ),
                ),
              );
            } else {
              return Container();
            }
          },
        ),
      ),
    );
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


  Future<String> fetchVideoInfo() async{
    try{
      var video = await fetch(widget.videoId);
      if(video['url'] != null){
        return video['url'];
      }
      return null;
    } catch(e){
      return null;
    }
  }

  Future<void> initializePlayer(String videoUrl) async {
    try {
      setState(() {
        showLoading = true;
      });
      _videoPlayerController = VideoPlayerController.network(videoUrl);
      await _videoPlayerController.initialize();
      _chewieController = ChewieController(
        videoPlayerController: _videoPlayerController,
        autoPlay: true,
        customControls: CustomVideoControls(),
        materialProgressColors: ChewieProgressColors(
          playedColor: Colors.red,
          handleColor: Colors.red,
          bufferedColor: Colors.white,
        )
      );
      setState(() {
        showLoading = false;
        isVideoInit = true;
      });
    } catch (e) {
      Navigator.of(context).push(MaterialPageRoute(
          builder: (_) => VideoScreen(widget.videoId)));
    }
  }

  Widget _buildPlayer(ConnectionStatus connectionStatus) {
    if (!isVideoInit && !showLoading) {
      return Stack(
        children: [
          Container(
            width: MediaQuery.of(context).size.width,
            child: FadeInImage.assetNetwork(
              placeholder: 'assets/images/loading.gif',
              image:
                  "https://img.youtube.com/vi/${widget.videoId}/sddefault.jpg",
              fit: BoxFit.fitWidth,
            ),
          ),
          Center(
            child: RaisedButton(
              color: Colors.red,
              child: Icon(
                Icons.play_arrow,
                color: Colors.white,
                size: 30,
              ),
              onPressed: !gettingVideoInfo? () async{
                if (connectionStatus.isConnected) {
                  setState(() {
                    gettingVideoInfo = true;
                  });
                  String videoUrl = await fetchVideoInfo();
                  if(videoUrl != null){
                    initializePlayer(videoUrl);
                  } else {
                    Navigator.of(context).push(MaterialPageRoute(
                        builder: (_) => VideoScreen(widget.videoId)));
                  }
                }
              }: null,
              shape: CircleBorder(),
            ),
          ),
        ],
      );
    }

    if (showLoading) {
      return Container(
        color: Colors.black,
        child: Center(
          child: CircularProgressIndicator(backgroundColor: Colors.red),
        ),
      );
    }

    if (isVideoInit) {
      return Container(
        child: Chewie(
          controller: _chewieController,
        ),
      );
    }
  }

  @override
  void dispose() {
    _videoPlayerController?.dispose();
    _chewieController?.dispose();
    super.dispose();
  }
}
