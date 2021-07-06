export class DeviceModel{
  public deviceId : number;
  public babyName : string;
  public tempReads : [];
  public heartbeatRate : [];
  public oxygenBloodLevel : [];
  public babyPosition : number;
  public eventSource : EventSource;
}
