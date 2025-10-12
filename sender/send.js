const admin = require('firebase-admin');
const fs = require('fs');
const path = require('path');

const keyPath = path.join(__dirname, 'service-account.json');
if (!fs.existsSync(keyPath)) {
  console.error('⚠️ ضع service-account.json في sender/');
  process.exit(1);
}
admin.initializeApp({ credential: admin.credential.cert(require(keyPath)) });

(async () => {
  const mode = process.argv[2] || 'topic';
  if (mode === 'token') {
    const token = process.argv[3];
    if (!token) { console.error('اكتب: node send.js token <DEVICE_FCM_TOKEN>'); process.exit(1); }
    const res = await admin.messaging().send({
      token,
      notification:{ title:'تنويه', body:'رسالة لهذا الجهاز' },
      android:{ priority:'high' },
      data:{ title:'تنويه', body:'رسالة لهذا الجهاز' }
    });
    console.log('تم الإرسال:', res);
  } else {
    const res = await admin.messaging().send({
      topic:'announcements',
      notification:{ title:'تنويه مهم', body:'رسالة عامة' },
      android:{ priority:'high' },
      data:{ title:'تنويه مهم', body:'رسالة عامة' }
    });
    console.log('تم الإرسال:', res);
  }
})().catch(e => { console.error(e); process.exit(1); });
