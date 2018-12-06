// pages/addOrUpdateCard/addOrUpdateCard.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    firstName:"",
    wechatNumber:"2",
    mobilePhoneNumber:"e",
    organization:"r",
    honor:"t",
    email:"t",
    address:"",
    lawNum:"45"
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
  chooseLocation() {
    const that = this
    wx.chooseLocation({
      success(res) {
        console.log(res)
        that.setData({
          // hasLocation: true,
          // location: formatLocation(res.longitude, res.latitude),
          address: res.address
        })
      }
    })
  },

  formSubmit:function(e){
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    const data = e.detail.value
    data.id = app.globalData.userInfo.id;
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/update",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log(res)
        app.globalData.userInfo = res.data.data
        console.log(app.globalData.userInfo);
        wx.navigateTo({
          url: '/pages/addOrUpdateCard/components/resume/resume'
        })
      }
    })

  }
})