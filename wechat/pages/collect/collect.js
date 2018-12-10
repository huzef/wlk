// pages/collect/collect.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    lawList: [],
    loading: false,
    more: false,
    moreBottom: false,
    void1: false,
    pageNum: 1,
  },

  getDetails: function (data) {
    const id = data.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/cardDetails/cardDetails?id=${id}`
    })

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
    if (!this.data.moreBottom) {
      this.setData({
        pageNum: ++this.data.pageNum,
        loading: true
      })
      this.init()
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  init: function(){
    const data = {pageNum: this.data.pageNum};
    var that = this;
    wx.request({
      url: app.globalData.baseService + "collection/page",
      header: {
        "Content-Type": "application/x-www-form-urlencoded", 'Cookie': wx.getStorageSync("cookie")
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        if(res.data.code=200){
          let list = []
          if (res.data.data.records.length == 0) {
            list = res.data.data.records
          } else {
            list = that.data.lawList.concat(res.data.data.records)
          }
          const lgth = res.data.data.records.length;
          that.setData({
            lawList: list,
            spinShow: false,
            moreBottom: (lgth < 10 ? true : false),
            more: ((lgth < 10 && lgth > 0) ? true : false),
            void1: (lgth == 0 ? true : false),
            loading: false
          })
        }else{
          app.loginCode();
        }


      }
    })
  },
  addCollect:function(){
    wx.switchTab({
      url: '/pages/repository/repository',
    }); 
  }
})