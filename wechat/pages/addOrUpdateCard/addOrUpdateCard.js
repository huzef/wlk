// pages/addOrUpdateCard/addOrUpdateCard.js
var app = getApp();
const { $Toast } = require('../../dist/base/index');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    user:{}
    // firstName: "",
    // wechatNumber:"",
    // mobilePhoneNumber:"",
    // organization:"",
    // honor:"",
    // email:"",
    // address:"",
    // lawNum:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.WxValidate = app.wxValidate(
      {
        firstName: { required: true},
        organization: { required: true },
        lawNum: { required: true },
        mobilePhoneNumber: { required: true, tel: true,},
        wechatNumber: { required: true },
        email: { required: true, email:true },
        address: { required: true },
        honor: { required: true },
      }, 
      {
        firstName: { required: '请输入您的姓名',},
        organization: { required: '请输入您的律所', },
        lawNum: { required: '请输入您的执业证号' },
        mobilePhoneNumber: { required: '请输入您的手机号' },
        wechatNumber: { required: '请输入您的微信号' },
        email: { required: '请输入您的邮箱' },
        address: { required: '请选择您的地址' },
        honor: { required: '请输入您的头衔' },
      }
    )


  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.init();
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
    //提交错误描述
    if (!this.WxValidate.checkForm(e)) {
      const error = this.WxValidate.errorList[0]
      // `${error.param} : ${error.msg} `
      $Toast({
        content: `${error.msg} `,
        type: 'error'
      });
      return false
    }

    const data = e.detail.value
    data.id = app.globalData.userInfo.id;
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
        console.log(res)
        if(res.data.code=200){
          app.globalData.userInfo = res.data.data
          console.log(app.globalData.userInfo);
          wx.navigateTo({
            url: '/pages/addOrUpdateCard/components/resume/resume'
          })
        }else{
          app.loginCode();
        }
        
      }
    })

  },
  init:function(){
    const data = {};
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
            user: res.data.data,
            address: res.data.data ? res.data.data.address : ""
          })
        }else{
          app.loginCode();
        }


      }
    })
  },
  nextTime:function(){
    // if (app.globalData.userInfo.isLaw==1){
    //   wx.switchTab({ url: '/pages/card/card', });
    // } else if (app.globalData.userInfo.isLaw == 2){
      wx.switchTab({
        url: '/pages/repository/repository',
      }); 
    // }
  }
})