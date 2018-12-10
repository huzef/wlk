// pages/addOrUpdateCard/components/case/case.js
var app = getApp();
const { $Toast } = require('../../../../dist/base/index');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cases:""
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
    this.init()
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
    data.isLaw=1
    if (data.cases == null || data.cases == "" || data.cases == undefined) {
      $Toast({
        content: "请输入您的案例！",
        type: 'error'
      });
      return false;
    }
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/update",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if(res.data.code==200){
          app.globalData.userInfo = res.data.data
          wx.switchTab({
            url: "/pages/card/card"
          })
        }else{
          app.loginCode()
        }

      }
    })

  },
  init: function () {
    const data = {};
    data.caseId = 1;
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/getOneOfUpdate",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if(res.data.code==200){
          that.setData({
            cases: res.data.data.wcases[0].content
          })
        }else{
          app.loginCode()
          
        }



      }
    })
  },

})