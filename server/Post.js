const mongoose = require('mongoose');

                                              

                                                          

const postSchema = new mongoose.Schema({
  content: { type: String, required: true },
  mediaUrls: [String],
  scheduledTime: { type: Date, required: true },
  platforms: [String],
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  status: { type: String, enum: ['scheduled', 'posted', 'failed'], default: 'scheduled' }
});

module.exports = mongoose.model('Post', postSchema);