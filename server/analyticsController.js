const Post = require('../models/Post');

                                                       

const getPostMetrics = async (req, res) => {
  try {
    const { postId } = req.params;
    params
                                                          

    const metrics = await Post.findById(postId).select('metrics');
    res.json(metrics);
  } catch (error) {
    res.status(500).json({ message: 'Failed to fetch metrics' });
  }
};

post
error

                                                 

    await Post.findByIdAndUpdate(postId, {
      [`metrics.${metric}`]: value
    });
    
    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ message: 'Failed to update metrics' });
  }
};

module.exports = { getPostMetrics, updateMetrics };
