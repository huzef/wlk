const config = require('../../utils/config.js');
var app = getApp();
Page({
  data: {
    current: '1',
    tab1: true,
    name: '',
    angle: 0,
    userInfo: { avatarUrl: "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJJsiajdymBxw4JT6B1d9DzVkempe1VvRYiaSuuFB4E63xsy9Y0X1icne8a3RQlmD7kHrMcDibfEckrlg/0",
      address:"深圳市 南山区 沿山路22号 火炬大厦 2层203室 ",
      company:"德高行(北京)科技有限公司"},
    imgUrls: [
      'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJJsiajdymBxw4JT6B1d9DzVkempe1VvRYiaSuuFB4E63xsy9Y0X1icne8a3RQlmD7kHrMcDibfEckrlg/0"
    ],
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 1000
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
    if (detail.key == 1) {
      this.setData({
        tab1: true,
        tab2: false
      })
    } else if (detail.key== 2) {
      this.setData({
        tab1: false,
        tab2: true
      })
    }
  },
  onLoad: function () {
  },
  onShow: function () {
    let that = this
    let userInfo = wx.getStorageSync('userInfo')
    if (!userInfo) {
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
          })
        }
      })
    } else {
      that.setData({
        userInfo: userInfo
      })
    }
  },
  onReady: function () {
    var that = this;
    wx.onAccelerometerChange(function (res) {
      var angle = -(res.x * 30).toFixed(1);
      if (angle > 14) {
        angle = 14;
      } else if (angle < -14) {
        angle = -14;
      }
      if (that.data.angle !== angle) {
        that.setData({
          angle: angle
        });
      }
    });
  },
  getLocation:function(e){
    console.log(this.data.userInfo.address)
    wx.request({
      url: `https://apis.map.qq.com/ws/geocoder/v1/?address=${this.data.userInfo.address}&key=${config.key}`,
      success: res => {
         console.log(res)
        wx.openLocation({
          longitude: Number(res.data.result.location.lng),
          latitude: Number(res.data.result.location.lat),
          name: this.data.userInfo.company,
          address: this.data.userInfo.address
        })
        // console.log(res.data.result.ad_info.city+res.data.result.ad_info.adcode);
        // that.selectCounty();
      }
    })
  },
  addContact:function(){
    wx.addPhoneContact({
      firstName:"张三",
      mobilePhoneNumber:"13026640441",
      success() {
        wx.showToast({
          title: '联系人创建成功'
        })
      },
      fail() {
        wx.showToast({
          title: '联系人创建失败'
        })
      }
    })
  },
  makePhoneCall() {
    wx.makePhoneCall({
      phoneNumber: '13026610440',
      success() {
        console.log('成功拨打电话')
      }
    })
  },
  onShareAppMessage() {
    return {title: '张三三的名片',
      desc: '自定义转发描述',
      path: '/pages/card/card'}
  }
});