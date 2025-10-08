// Global configuration object for the attendance PWA.
window.APP_CONFIG = {
  // Your OneSignal App ID. This must match the App ID from your OneSignal dashboard.
  ONE_SIGNAL_APP_ID: "cb392247-19c2-4e2d-bc70-e89ff4f14192",
  // Optional: endpoint to send RSVP data to a Google Apps Script. Leave empty if not used.
  RSVP_ENDPOINT: "",
  // Text of the daily attendance question shown in the site and notifications.
  NOTIFY_TEXT: "هل ستحضر غدًا إلى العمل؟",
  // Options for the RSVP page. Each option has a label and an answer value.
  OPTIONS: [
    { label: "نعم سأحضر", answer: "yes" },
    { label: "سأعمل من الخارج", answer: "remote" },
    { label: "لا لن أحضر", answer: "no" }
  ]
};