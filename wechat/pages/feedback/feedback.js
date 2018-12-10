// pages/feedback/feedback.js
var app = getApp();
const { $Toast } = require('../../dist/base/index');
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    const data = e.detail.value
    if (data.content == null || data.content == "" || data.content == undefined) {
      $Toast({
        content: "请输入您的想法！",
        type: 'error'
      });
      return false;
    }
    var that = this;
    wx.request({
      url: app.globalData.baseService + "feedback/add",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if (res.data.code == 200) {
          $Toast({
            content: "感谢您的支持，我们会尽快处理的！",
            type: 'success'
          });
        } else {
          app.loginCode()
        }

      }
    })

  }
})