package view;
public class StudentProfile extends javax.swing.JPanel {
    public StudentProfile() {
        initComponents();
        emailTextField.setEditable(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        photo = new javax.swing.JLabel();
        lastName = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        firstName = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        email = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        gender = new javax.swing.JLabel();
        genderTextField = new javax.swing.JTextField();
        contact = new javax.swing.JLabel();
        contactTextField = new javax.swing.JTextField();
        address = new javax.swing.JLabel();
        addressTextField = new javax.swing.JTextField();
        course = new javax.swing.JLabel();
        courseTextField = new javax.swing.JTextField();
        course1 = new javax.swing.JLabel();
        batchTextField = new javax.swing.JTextField();
        course2 = new javax.swing.JLabel();
        yearTextField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(910, 630));
        setMinimumSize(new java.awt.Dimension(910, 630));

        photo.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        photo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photo.setText("Photo");

        lastName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lastName.setText("Last Name");

        lastNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lastNameTextField.setText("  Random Name");
        lastNameTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lastNameTextField.setRequestFocusEnabled(false);
        lastNameTextField.addActionListener(this::lastNameTextFieldActionPerformed);

        firstName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        firstName.setText("FIrst Name");

        firstNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        firstNameTextField.setText("  Random Name");
        firstNameTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        firstNameTextField.setRequestFocusEnabled(false);
        firstNameTextField.addActionListener(this::firstNameTextFieldActionPerformed);

        email.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        email.setText("Email");

        emailTextField.setBackground(new java.awt.Color(255, 255, 255));
        emailTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        emailTextField.setForeground(new java.awt.Color(0, 0, 0));
        emailTextField.setText("  Random Name");
        emailTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        emailTextField.setCaretColor(new java.awt.Color(255, 255, 255));
        emailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        emailTextField.addActionListener(this::emailTextFieldActionPerformed);

        gender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        gender.setText("Gender");

        genderTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        genderTextField.setText("  Random Name");
        genderTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        genderTextField.setRequestFocusEnabled(false);
        genderTextField.addActionListener(this::genderTextFieldActionPerformed);

        contact.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        contact.setText("Contact");

        contactTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        contactTextField.setText("  Random Name");
        contactTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        contactTextField.setRequestFocusEnabled(false);
        contactTextField.addActionListener(this::contactTextFieldActionPerformed);

        address.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        address.setText("Address");

        addressTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addressTextField.setText("  Random Name");
        addressTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        addressTextField.setRequestFocusEnabled(false);
        addressTextField.addActionListener(this::addressTextFieldActionPerformed);

        course.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        course.setText("Course");

        courseTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        courseTextField.setText("  Random Name");
        courseTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        courseTextField.setRequestFocusEnabled(false);
        courseTextField.addActionListener(this::courseTextFieldActionPerformed);

        course1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        course1.setText("Batch");

        batchTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        batchTextField.setText("  Random Name");
        batchTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        batchTextField.setRequestFocusEnabled(false);
        batchTextField.addActionListener(this::batchTextFieldActionPerformed);

        course2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        course2.setText("Year");

        yearTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        yearTextField.setText("  Random Name");
        yearTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        yearTextField.setRequestFocusEnabled(false);
        yearTextField.addActionListener(this::yearTextFieldActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(course1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(302, 302, 302))
                            .addComponent(batchTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(course2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(302, 302, 302))
                            .addComponent(yearTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contactTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(302, 302, 302))
                            .addComponent(courseTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(genderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(course, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(courseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(course1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(batchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(course2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void genderTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderTextFieldActionPerformed

    private void contactTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactTextFieldActionPerformed

    private void addressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressTextFieldActionPerformed

    private void courseTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseTextFieldActionPerformed

    private void batchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_batchTextFieldActionPerformed

    private void yearTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel address;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JTextField batchTextField;
    private javax.swing.JLabel contact;
    private javax.swing.JTextField contactTextField;
    private javax.swing.JLabel course;
    private javax.swing.JLabel course1;
    private javax.swing.JLabel course2;
    private javax.swing.JTextField courseTextField;
    private javax.swing.JLabel email;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel firstName;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel gender;
    private javax.swing.JTextField genderTextField;
    private javax.swing.JLabel lastName;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JLabel photo;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration//GEN-END:variables
  public void setFirstName(String firstName)
  {
      firstNameTextField.setText("  "+firstName);
  }
    public void setLastName(String lastName)
    {
        lastNameTextField.setText("  "+lastName);
    }

  public void setEmail(String email)
  {
      emailTextField.setText("  "+email);
  }

  public void setAddress(String address)
  {
      addressTextField.setText("  "+address);
  }

  public void setContact(String contact)
  {
      contactTextField.setText("  "+contact);
  }

  public void setGender(String gender)
  {
      genderTextField.setText("  "+gender);
  }

  public void setCourse(String course)
  {
      courseTextField.setText("  "+course);
  }

   public void setBatch(String batch)
  {
      batchTextField.setText("  "+batch);
  }
   
    public void setYear(String year)
  {
      yearTextField.setText("  "+year);
  }
}
